# Katalon Studio API Testing - OpenWeatherMap

## Project Overview
Project ini dibuat menggunakan **Katalon Studio** untuk menguji API dari **OpenWeatherMap**, khususnya fitur **Air Pollution** dan **Weather Forecast**.

## Project Structure
Berikut adalah struktur folder utama dalam project ini:

```
├── Profiles
│   ├── default (Default test profile)
│   ├── staging (Staging test profile)
│
├── Test Cases
│   ├── AirPollution
│   │   ├── TC_GetCurrentAirPollution (Test case untuk Air Pollution API)
│   ├── ForecastWeather
│   │   ├── TC_Get5DayForecastWeather (Test case untuk 5-day Weather Forecast API)
│
├── Object Repository
│   ├── AirPollution
│   │   ├── SR_GetCurrentAirPollution (Request object untuk mendapatkan data polusi udara)
│   │   ├── SR_GetCurrentAirPollution_Invalid (Invalid request object / endpoint)
│   ├── ForecastWeather
│   │   ├── SR_Get5DayForecastWeather (Request object untuk mendapatkan cuaca 5 hari ke depan)
│   │   ├── SR_Get5DayForecastWeather_Invalid (Invalid request object / endpoint)
│
├── Test Suites
│   ├── TS_OpenWeatherMap (Test suite untuk menjalankan semua test case)
│
├── Reports (Folder untuk menyimpan hasil laporan pengujian) <<<<<<<<<<<<<<<<<<<<<<<< CHECK REPORT DI SINI!
│
├── Keywords
│
├── Include
│
├── Plugins
│
├── .gitignore
├── build.gradle
├── console.properties
```

## Cara Menjalankan Project
1. **Clone repository ini**
   ```sh
   git clone https://github.com/dimas-sulaksono/TechnicalTest-QA-ADL.git
   cd TechnicalTest-QA-ADL
   ```
2. **Buka Katalon Studio**
3. **Import project ini**
   - Klik **File** → **Open Project** → Pilih folder project
4. **Konfigurasi API Key**
   - Masukkan API key dari OpenWeatherMap di **Profiles > default** atau **Profiles > staging**
   - Ubah nilai **GlobalVariable** `apiKey` dengan API key yang valid
5. **Jalankan Test Suite**
   - Buka **Test Suites > TS_OpenWeatherMap** → Klik **Run**

## Cara Mendapatkan Laporan Hasil Pengujian
1. Setelah menjalankan **Test Suite**, laporan akan otomatis dibuat di folder:
   ```
   Reports/
   ```
2. Untuk melihat laporan:
   - **Buka Katalon Studio** → **Reports** → Pilih laporan terbaru (Khusus jika Katalon kamu memiliki lisensi Enterprise!)
   - Atau buka folder `Reports/` menggunakan File Explorer (Windows) / Finder (Mac OS) dan cari file laporan `.html` atau `.csv`
     - Untuk file `.html` kamu bisa langsung membukanya di browser
     - Untuk `.csv` kamu perlu merubah text to column untuk bisa membaca laporannya

## Teknologi yang Digunakan
- **Katalon Studio**
- **Groovy** (Bahasa pemrograman untuk scripting di Katalon)
- Menggunakan API dari **OpenWeatherMap**

## Catatan Penting
- Pastikan Anda memiliki API key dari [OpenWeatherMap](https://home.openweathermap.org/api_keys) sebelum menjalankan pengujian.
- Jika mengalami error, periksa kembali **Profiles** untuk memastikan API key sudah dikonfigurasi dengan benar.


