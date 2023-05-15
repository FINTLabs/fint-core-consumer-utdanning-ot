# FINT Core Consumer Utdanning Ot

## Configuration

Set `spring.kafka.consumer.group-id` as enviroment variable before you run. Should be uniq for each (development) enviroment. If multiple services runs with the same group-id, it is random which client will receive the response.