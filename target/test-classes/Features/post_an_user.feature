Feature: User REST Api Requests

@ApiPost
Scenario: Post an user method test
	Given content type is JSON and accept type is JSON
	When I post a new user with username "Bret"
	Then status code should be 201
	And response Json should contain user info
	When I send a GET request with username "Bret" 
	Then status code should be 200
	And user Json response data should match the posted Json data