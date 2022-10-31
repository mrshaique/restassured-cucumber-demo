package stepdefs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.LinkedHashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.StringUtils;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class BookStepDefinitions {

	private Response response;
	private ValidatableResponse json;
	private RequestSpecification request;
	private String[] URL_Arr = new String[] {"","https://www.googleapis.com/books/v1/volumes","https://catfact.ninja/fact", "https://api.coindesk.com/v1/bpi/currentprice.json", "https://40a53909-ebba-41f7-ac45-7a7a400feb18.mock.pstmn.io"};

	@Given("the user can query by isbn (.*)")
	public void a_book_exists_with_isbn(String isbn){
		request = given().param("q", "isbn:" + isbn);
	}

	@Given("a book exists with an title of (.*)")
	public void a_book_exists_with_title(String title){
		request = given().param("q", ""  + title);
		System.out.println(request);
	}

	@Given("the API exists")
	public void api_exists() {
		request = given().param("q", "x");
	}

	@When("a user retrieves the book by Title")
	public void a_user_retrieves_the_book_by_Title(){
		response = request.when().get(URL_Arr[1]);
		System.out.println("response: " + response.prettyPrint());
	}

	@When("A user gets info from the API (\\d+)")
	public void a_user_gets_response_from_API(int apiNum){
		response = request.when().get(URL_Arr[apiNum]);
		//System.out.println("response: " + response.prettyPrint());
	}

	@Then("the status code is (\\d+)")
	public void verify_status_code(int statusCode){
		json = response.then().statusCode(statusCode);
	}

	@And("response includes values above these thresholds$")
	public void response_above(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			json.body(field.getKey(), greaterThanOrEqualTo(Integer.parseInt(field.getValue())));
			}
		}

	@And("response includes values below these thresholds$")
	public void response_below(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			json.body(field.getKey(), lessThanOrEqualTo(Integer.parseInt(field.getValue())));
		}
	}

	@And("the price is checked in the currency (.*)")
		public void query_price_dynam(String region){
		JsonPath responsePath = response.jsonPath();
		LinkedHashMap<String, LinkedHashMap<String, String>> bpiPath = responsePath.get("bpi");
		LinkedHashMap<String, String> USPath = bpiPath.get(region);
		String price = USPath.get("rate");
		System.out.println("Price received from Response is: " + price + " " + region);
	}

	@And("the user checks the price")
	public void query_price(){
		JsonPath responsePath = response.jsonPath();
		LinkedHashMap<String, LinkedHashMap<String, String>> bpiPath = responsePath.get("bpi");
		LinkedHashMap<String, String> USPath = bpiPath.get("USD");
		String price = USPath.get("rate");
		System.out.println("Price received from Response $" + price );
	}

	@And("the user conducts static post to the API (\\d+)")
	public void user_post_api_static_full(int URL_num){
		RestAssured.baseURI = URL_Arr[URL_num];
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.post("/ping");
		System.out.println("response: " + response.prettyPrint());
	}

	@And("the user is posting to the API (\\d+)")
	public void user_post_api(int URL_num){
		RestAssured.baseURI = URL_Arr[URL_num];
		request = RestAssured.given();
		request.header("Content-Type", "application/json");
	}

	@And("the user posts to the API with path (.*)")
	public void user_post_api_with_path(String path) {
		Response response = request.post(path);
		System.out.println("response: " + response.prettyPrint());
	}

	@And("response includes the following$")
	public void response_equals(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			}
			else{
				json.body(field.getKey(), equalTo(field.getValue()));
			}
		}
	}

	@And("response includes the following in any order")
	public void response_contains_in_any_order(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				json.body(field.getKey(), containsInAnyOrder(Integer.parseInt(field.getValue())));
			}
			else{
				json.body(field.getKey(), containsInAnyOrder(field.getValue()));
			}
		}
	}



}


