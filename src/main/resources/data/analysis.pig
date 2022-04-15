log = LOAD 'hdfs://quoctuan-master:54310/pigdata/app$file.log' USING PigStorage('\t')
as (ip:chararray,date:chararray,request:chararray,status:int,size:int,user:chararray);

log_group = group log by request;
log_count = foreach log_group Generate MIN(log.date), MAX(log.date), FLATTEN(log.request), COUNT(log.request), AVG(log.size);
log_dist = distinct log_count;

STORE log_dist INTO 'hdfs://quoctuan-master:54310/pigdata/output$file';
