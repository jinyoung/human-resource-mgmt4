# human-resource-mgmt4

```
http :8083/employees userId="jjy" name="jinyoung" email="jyjang@uengine.org"
http :8081/vacationDaysStatuses
http :8081/vacations userId="jjy_" days=3 reason="travel"
http :8081/vacationStatuses  
http :8081/vacationDaysStatuses  # remains 7 days
http PUT :8081/vacations/0643f468-9711-42ff-adf2-277b9f7db6e8/approve approve=true
http :8081/vacationDaysLefts/jjy_/events
```