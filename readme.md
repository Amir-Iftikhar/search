# To run project


#### 1.Make sure you have the trunarrative.xapikey value in file src/main/resources/secrets.properties, set to your x-api-key value.
#### 2.From a terminal run command 'mvn spring-boot:run'

### Graphical Interface available here 'http://localhost:8080/TruProxyAPI/Company/Search'

### Api calls can be accessed via curl commands.

Example curl for non active company with activeCompaniesOnly defaulted to false. Will return Company data
curl --location 'http://localhost:8080/TruProxyAPI/Company/Search' \
--header 'Content-Type: application/json' \
--data '{
"companyName" : "TRUNARRATIVE LTD",
"companyNumber" : "10241297"
}'




#Example curl for non active company with activeCompaniesOnly defaulted to true. Will NOT return Company data
curl --location 'http://localhost:8080/TruProxyAPI/Company/Search?activeCompaniesOnly=true' \
--header 'Content-Type: application/json' \
--data '{
"companyName" : "TRUNARRATIVE LTD",
"companyNumber" : "10241297"
}'

#   p r o j e c t s 
 
 
