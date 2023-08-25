quartz

## 添加job
```
http://127.0.0.1:8080/test/addJob

{
"jobName":"materialJob",
"groupName":"material-from-eam",
"jobClass":"com.example.yxy.job.WasCommandJob",
"cronExpression":"0/5 * * * * ? "
}
```