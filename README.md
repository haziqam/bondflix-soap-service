# Bondflix SOAP Service

## Deskripsi Singkat
Bondflix SOAP Service adalah sebuah server yang menyediakan layanan SOAP API untuk REST Client dan PHP Client.

## Skema Basis Data
![Schema](/asset/SchemaSOAP.png)

## Endpoint API
Endpoint API dapat dilihat pada link ini: https://documenter.getpostman.com/view/30701742/2s9YXpUyWT

## Pembagian Tugas
**Anggota Kelompok**

| Nama                   | NIM      | Panggilan |
| ---------------------- | -------- | --------- |
| Cetta Reswara Parahita | 13521133 | Cetta     |
| Nicholas Liem          | 13521135 | Nicholas  |
| Haziq Abiyyu Mahdy     | 13521170 | Haziq     |

| NIM      | Nama     | Fungsionalitas |
|----------|----------|----------------|
| 13521135 | Nicholas | Semua          |
| 13521170 | Haziq    |                |  


## Cara Menginstall dan Menjalankan Program - How to Install and Run The Program

1. Clone this repository
```sh
https://gitlab.informatika.org/if3110-2023-k01-02-24/bondflix-soap-service.git
```

2. Change the current directory to 'bondflix-soap-service' folder
```sh
cd bondflix-soap-service
```

3. Make a new .env file based on .env.example both for the docker and inside 'src folder'
```sh
mv .env.example .env && mv docker.env.example docker.env
```

4. Build and run your docker containers
```sh
docker-compose up -d --build
```
