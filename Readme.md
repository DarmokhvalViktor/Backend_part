maven -> package, if java app is changed. after docker-compose all

frontend -> npm run build(to compile into frontend directory), after docker restart web.

npm run watch - to run script that will follow the changes in react app and update in browser
(before compile web exit from watch and execute npm run build).
flyway migration: maven->flyway->flyway:migrate

to deploy project on AWS Beanstalk: zip files/directories into one file:

1) backend (backend part)
2) client (frontend part)
3) mysql_database (database)
4) docker-compose.yml
5) Dockerfile
6) nginx.conf
7) wait-for-it.sh