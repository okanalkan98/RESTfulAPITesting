Feature: POST then GET

@Temp
Scenario Outline: POST user then GET user
Given Content type is json
And Accept type is json
When I post a new user with username "<userName>"
Then Status code should be <postStatusCode>
And Response Json should contain user info
When I send a GET request with username "<userName>"
Then Status code should be <getStatusCode>
And user Json response data should match the posted Json data

Examples: 
|userName|postStatusCode|getStatusCode|
|Bret    |201           |200          |