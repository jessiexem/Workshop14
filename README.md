to display list of contacts:
http://localhost:8080/contact?startIndex=0

to display contact by id
http://localhost:8080/getContact/24399972

Command prompt: 

C:\Program Files\Redis>
redis-cli -h localhost -p 6379

- To display all the contact Ids:
hkeys contactlist_Map