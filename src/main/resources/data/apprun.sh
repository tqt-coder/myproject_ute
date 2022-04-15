# clear local folder location 
rm -r *.log
rm -r *.pig

# clear hadoop folder location
hadoop fs -rm -r -skipTrash /pigdata
hadoop fs -mkdir /pigdata

# download pig script file and upload on hadoop
wget http://$1:$2/download/analysis.pig
hadoop fs -copyFromLocal analysis.pig /pigdata

echo "-->> Start, Pig Script.";
# run script in loop
for i in $(seq 1 5)
do
  # wait loop here for 30 second
	sleep 30

  echo "-->> Start, log analysis using pig script - $i";

	# download data file and upload in HDFS
  wget http://$1:$2/download/app$i.log
  hadoop fs -copyFromLocal app$i.log /pigdata

  # run pig script into hadoop
	pig -param file=$i hdfs://tuanmanh-master:9000/pigdata/analysis.pig

	# copy output file from hadoop to local
	hadoop fs -copyToLocal /pigdata/output$i/part-r-00000 part-r-00000_$i

  echo "-->> End, log analysis using pig script - $i";

	# upload result file
	curl -X POST http://$1:$2/upload -H 'content-type: multipart/form-data' -F file=@part-r-00000_$i

	echo "-->> Result file is uploaded - $i";

done
echo "-->> End, Pig Script.";