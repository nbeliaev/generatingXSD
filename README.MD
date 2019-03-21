# generatingXSD
The utility generates an xsd schema based on xml file.
## Supported features
- Scheme specification (Russian doll, Salami slice, Venetian blind)  
- Target namespace specification

## Download and compile 
* Download and install Java 8.
* Download and install [Apache Maven](http://maven.apache.org/)

* Clone this repository (or download directly from github)
```
git clone https://github.com/fr13Dev/generatingXSD.git
```
* Compile and run
```
cd metadataSorting
mvn package
java -jar xml2xsd.jar
```
## Usage
Open command line and type
```
java -jar xml2xsd.jar
```