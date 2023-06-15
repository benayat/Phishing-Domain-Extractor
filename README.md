# Phishing Domain Extractor

This project uses Spring Batch to extract domain names and URLs from Phishtank, and phishing.database databases and reported phishing links, and write them to a MongoDB database.

## Spring Batch

Spring Batch is a framework for building batch processing applications in Java. It provides a set of reusable components for reading, processing, and writing large volumes of data. Spring Batch is designed to handle complex processing scenarios, such as processing data in parallel, retrying failed operations, and handling transactions.

In this project, we use Spring Batch to read data from two different sources (a JSON file and a text file), process the data to extract domain names and URLs, and write the data to a MongoDB database. We define three steps in our batch job:

1. `jsonToRedisStep`: Reads data from the `verified_online.json` file, processes it using the `phishDetailsToUrlPojoProcessor`, and writes the results to redis with RedisFinalWriter using pipeline, to avoid multiple network calls.
2. `textToRedisPojoStep`: Reads data from the `All-phishing-links.txt` file, processes it using the `textToRedisPojoProcessor`, and writes the results like step 1.
3. `redisToMongoStep`: Reads data from redis using redisReader, processes it using the `redisToMongoProcessor`, and writes the results to the same MongoDB database using the `MongoDomainWriter`.

Each step consists of a reader, a processor, and a writer. The reader reads data from a source (in this case, a file), the processor processes the data (in this case, extracting domain names and URLs), and the writer writes the results to a destination (in this case, Redis or MongoDB database).

## Installation

To install and run this project, you'll need to have Java and Maven installed on your system. You'll also need to have a MongoDB and Redis instances running on default ports.

1. Clone the repository:

```
git clone https://github.com/your-username/phishing-domain-extractor.git
```

2. Build the project:

```
cd phishing-domain-extractor
mvn clean package
```

3. Run the project:

```
java -jar target/phishing-domain-extractor-1.0.jar
```

## Usage

When you run the project, it will read the `verified_online.json` file and the `All-phishing-links.txt` file, extract the domain name and URL for each entry, and write this data to a MongoDB database.
if the file doesn't exist, it will download it.
You can modify the MongoDB and Redis connection settings in the `application.properties` file.

## Contributing

If you'd like to contribute to this project, please fork the repository and create a pull request. You can also report bugs or suggest new features by opening an issue.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

If you have any questions or comments about this project, please contact the project maintainer at `benaya7@gmail.com`.
