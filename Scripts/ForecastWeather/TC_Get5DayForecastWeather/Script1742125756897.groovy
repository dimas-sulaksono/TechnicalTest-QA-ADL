import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import groovy.json.JsonSlurper as JsonSlurper
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject

def lat = '-6.225'
def lon = '106.8296'
def apiKey = GlobalVariable.apiKey
def invalidApiKey = GlobalVariable.invalidApiKey
def object = 'Object Repository/ForecastWeather/SR_Get5DayForecastWeather'
def invalidObject = 'Object Repository/ForecastWeather/SR_Get5DayForecastWeather_Invalid'

// expected message
def message_400_InvalidLat = 'wrong latitude'
def message_400_InvalidLon = 'wrong longitude'
def message_400_NoGeocode = 'Nothing to geocode'
def message_401 = 'Invalid API key. Please see https://openweathermap.org/faq#error401 for more info.'
def message_404 = 'Internal error'

// reusable function untuk validasi status code & response 
def validateResponse(ResponseObject response, int expectedStatus, String expectedMessage = null) {
	try {
		// verifikasi status code
		WS.verifyResponseStatusCode(response, expectedStatus, FailureHandling.STOP_ON_FAILURE)
		
		// error handling jika response kosong
		def responseBody = response.getResponseText()
		if (responseBody == null || responseBody.trim().isEmpty()) {
			KeywordUtil.markFailedAndStop("Error: Response kosong atau tidak valid!")
		}
		def jsonResponse = new JsonSlurper().parseText(responseBody)
		def cod = WS.getElementPropertyValue(response, 'cod')
		def message = WS.getElementPropertyValue(response, 'message')
		
		
		// validasi berdasarkan status code - cek apakah message sesuai dengan API Docs
		switch (expectedStatus) {
			case 200:
				// Pastikan response memiliki properti utama
		        assert jsonResponse.containsKey('cod') : "Error: Properti 'cod' tidak ditemukan!"
		        assert jsonResponse.containsKey('message') : "Error: Properti 'message' tidak ditemukan!"
		        assert jsonResponse.containsKey('cnt') : "Error: Properti 'cnt' tidak ditemukan!"
		        assert jsonResponse.containsKey('list') : "Error: Properti 'list' tidak ditemukan!"
		
		        // Pastikan setiap item dalam list memiliki properti yang diharapkan
		        jsonResponse.list.each { item ->
		            assert item.containsKey('dt') : "Error: Properti 'list.dt' tidak ditemukan!"
		            assert item.containsKey('main') : "Error: Properti 'list.main' tidak ditemukan!"
		            assert item.containsKey('weather') : "Error: Properti 'list.weather' tidak ditemukan!"
		            assert item.containsKey('clouds') : "Error: Properti 'list.clouds' tidak ditemukan!"
		            assert item.containsKey('wind') : "Error: Properti 'list.wind' tidak ditemukan!"
		            assert item.containsKey('visibility') : "Error: Properti 'list.visibility' tidak ditemukan!"
		            assert item.containsKey('pop') : "Error: Properti 'list.pop' tidak ditemukan!"
		            assert item.containsKey('sys') : "Error: Properti 'list.sys' tidak ditemukan!"
		            assert item.containsKey('dt_txt') : "Error: Properti 'list.dt_txt' tidak ditemukan!"
		
		            // Properti 'rain' bersifat opsional, cek hanya jika ada
		            if (item.containsKey('rain')) {
		                assert item.rain.containsKey('3h') : "Error: Properti 'list.rain.3h' tidak ditemukan!"
		            }
		        }
		
		        // Verifikasi properti dalam 'city'
		        assert jsonResponse.containsKey('city') : "Error: Properti 'city' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('id') : "Error: Properti 'city.id' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('name') : "Error: Properti 'city.name' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('coord') : "Error: Properti 'city.coord' tidak ditemukan!"
		        assert jsonResponse.city.coord.containsKey('lat') : "Error: Properti 'city.coord.lat' tidak ditemukan!"
		        assert jsonResponse.city.coord.containsKey('lon') : "Error: Properti 'city.coord.lon' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('country') : "Error: Properti 'city.country' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('population') : "Error: Properti 'city.population' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('timezone') : "Error: Properti 'city.timezone' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('sunrise') : "Error: Properti 'city.sunrise' tidak ditemukan!"
		        assert jsonResponse.city.containsKey('sunset') : "Error: Properti 'city.sunset' tidak ditemukan!"
		
		        KeywordUtil.logInfo("Semua properti yang ditemukan dalam response sesuai ekspektasi!")
		        break
			case 400:
				WS.verifyElementPropertyValue(response, 'message', expectedMessage, FailureHandling.CONTINUE_ON_FAILURE)
				KeywordUtil.logInfo("Actual: Status " + cod + " - " + message)
				KeywordUtil.logInfo("Expected: Status ${expectedStatus} - ${expectedMessage}")
				break
			case 401:
				WS.verifyElementPropertyValue(response, 'message', expectedMessage, FailureHandling.CONTINUE_ON_FAILURE)
				KeywordUtil.logInfo("Actual: Status " + cod + " - " + message)
				KeywordUtil.logInfo("Status ${expectedStatus} - ${expectedMessage}")
				break
			case 404:
				WS.verifyElementPropertyValue(response, 'message', expectedMessage, FailureHandling.CONTINUE_ON_FAILURE)
				KeywordUtil.logInfo("Actual: Status " + cod + " - " + message)
				KeywordUtil.logInfo("Status ${expectedStatus} - ${expectedMessage}")
				break
			case 500:
				KeywordUtil.markFailed("Status 500 - Internal Server Error!")
				break
			default:
				KeywordUtil.logInfo("Status ${expectedStatus} unknow!")
				break
		}
		
	} catch (Exception e) {
		KeywordUtil.markFailedAndStop("Error saat parsing response: " + e.message)
	}
}

// validasi response 200
RequestObject request_200 = findTestObject(object, [('lat'): lat, ('lon'): lon, ('apiKey'): apiKey])
validateResponse(WS.sendRequest(request_200), 200, '')


// validasi response 400 (invalid latitude)
RequestObject request_400_InvalidLat = findTestObject(object, [('lat') : 'abc', ('lon') : lon, ('apiKey') : apiKey])
validateResponse(WS.sendRequest(request_400_InvalidLat), 400, message_400_InvalidLat)


// Validasi Response 400 (Invalid Longitude)
RequestObject request_400_InvalidLon = findTestObject(object, [('lat') : lat, ('lon') : 'abc', ('apiKey') : apiKey])
validateResponse(WS.sendRequest(request_400_InvalidLon), 400, message_400_InvalidLon)


// Validasi Response 400 (No Geocode)
RequestObject request_400_NoGeocode = findTestObject(object, [('lat') : '', ('lon') : '', ('apiKey') : apiKey])
validateResponse(WS.sendRequest(request_400_NoGeocode), 400, message_400_NoGeocode)


// Validasi Response 401 (null / invalid)
RequestObject request_401 = findTestObject(object, [('lat') : lat, ('lon') : lat, ('apiKey') : invalidApiKey])
validateResponse(WS.sendRequest(request_401), 401, message_401)

// Validasi Response 404 (invalid endpoint)
RequestObject request_404 = findTestObject(invalidObject, [('lat') : lat, ('lon') : lat, ('apiKey') : apiKey])
validateResponse(WS.sendRequest(request_404), 404, message_404)
