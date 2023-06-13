# Phishing Domain Extractor

This project reads data from the `verified_online.json` file and the `All-phishing-links.txt` file, which contain the Phishtank database and reported phishing links, respectively. It extracts the domain name and URL for each entry and writes this data to a MongoDB database as documents of domain name and a list of URLs attached to it.

## Installation

To install and run this project, you'll need to have Java and Maven installed on your system. You'll also need to have a MongoDB instance running.

1. Clone the repository:

```
git clone https://github.com/your-username/phishing-domain-extractor.git
```

2. Build the project:

```
cd <project folder>
mvn clean package
```

3. Run the project:

```
java -jar target/<your project name>.jar
```

## Usage

When you run the project, it will read the `verified_online.json` file from the `src/main/resources` directory and the `All-phishing-links.txt` file from the `src/main/resources/data` directory. It will extract the domain name and URL for each entry and write this data to a MongoDB database.

You can modify the MongoDB connection settings in the `application.properties` file.
please note that this is just an example, for studying purposes. if you want something for production, make sure you update your database frequently, and write a better algorithm to make the requests faster.

## Contributing

If you'd like to contribute to this project, please fork the repository and create a pull request. You can also report bugs or suggest new features by opening an issue.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

If you have any questions or comments about this project, please contact the project maintainer at `benaya7@gmail.com`.

I hope this updated README helps you get started with your project! Let me know if you have any further questions.