# URL Shortener with Analytics

A comprehensive URL shortening web application built with Java 21, Spring Boot, and MongoDB, featuring real-time analytics, custom aliases, and interactive API documentation.

## 🚀 Features

- **URL Shortening**: Convert long URLs into short, shareable links
- **Custom Aliases**: Create branded short links with custom identifiers
- **Click Analytics**: Track clicks, geographical data, and referrer sources
- **Real-time Dashboard**: Interactive charts and visualizations
- **URL Expiration**: Set automatic expiration dates for links
- **RESTful API**: Complete API with Swagger documentation
- **Responsive Design**: Mobile-friendly web interface
- **MongoDB Integration**: Optimized database schema with indexing

## 🛠️ Technology Stack

- **Backend**: Java 21, Spring Boot 3.2, Spring Data MongoDB
- **Database**: MongoDB with optimized indexing
- **Frontend**: Thymeleaf, Bootstrap 5, Chart.js
- **API Documentation**: Swagger/OpenAPI 3, SpringDoc
- **Build Tool**: Maven
## 📋 Prerequisites

- Java 21 or higher
- MongoDB 4.4 or higher
- Maven 3.6 or higher

## 🔧 Installation & Setup

1. **Clone the repository**
   git clone <repository-url>
   cd url-shortener


2. **Start MongoDB**
   Using Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest

Or start local MongoDB service
mongod


3. **Build the project**
   mvn clean install



4. **Run the application**
   mvn spring-boot:run



5. **Access the application**
- **Web Interface**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

> The MongoDB URI and port are read from the `MONGODB_URI` and `PORT` environment
> variables, falling back to `mongodb://localhost:27017/url_shortener` and `8080`.

## ☁️ Deploy (Docker + Render + MongoDB Atlas)

The app is containerized (`Dockerfile`) and ships a Render blueprint (`render.yaml`).

1. **MongoDB Atlas** (free M0): create a cluster, a database user, and allow network
   access from `0.0.0.0/0`. Grab the connection string and append the database name,
   e.g. `mongodb+srv://<user>:<pass>@<cluster>.mongodb.net/url_shortener?retryWrites=true&w=majority`.
2. **Render** → New → **Blueprint** → point at this repo → it reads `render.yaml`.
3. Set the one secret env var **`MONGODB_URI`** to your Atlas string → **Apply**.

Or run the container locally against any Mongo:

```bash
docker build -t url-shortener .
docker run -p 8080:8080 -e MONGODB_URI="<your-atlas-uri>" url-shortener
```

## 🌐 API Endpoints

### URL Management
- `POST /api/shorten` - Create a shortened URL
- `GET /api/url/{shortId}` - Get URL details
- `GET /{shortId}` - Redirect to original URL

### Analytics
- `GET /api/analytics/{shortId}` - Get analytics data (JSON)
- `GET /analytics/{shortId}` - View analytics dashboard (Web)

## 📊 API Usage Examples

### Create Short URL
curl -X POST http://localhost:8080/api/shorten
-H "Content-Type: application/json"
-d '{
"url": "https://example.com/very/long/url",
"customAlias": "my-link",
"title": "Example Website",
"expiresInDays": 30
}'



### Get Analytics
curl http://localhost:8080/api/analytics/my-link



## 🗂️ Project Structure

url-shortener/
├── src/main/java/com/urlshortener/
│ ├── UrlShortenerApplication.java
│ ├── config/
│ │ ├── MongoConfig.java
│ │ └── OpenApiConfig.java
│ ├── controller/
│ │ ├── UrlController.java
│ │ └── AnalyticsController.java
│ ├── model/
│ │ ├── Url.java
│ │ ├── ClickEvent.java
│ │ └── AnalyticsResponse.java
│ ├── service/
│ │ ├── UrlService.java
│ │ └── AnalyticsService.java
│ ├── repository/
│ │ ├── UrlRepository.java
│ │ └── ClickEventRepository.java
│ ├── dto/
│ │ ├── UrlCreateRequest.java
│ │ └── UrlResponse.java
│ └── util/
│ └── ShortIdGenerator.java
└── src/main/resources/
├── application.yml
├── static/
│ ├── css/style.css
│ └── js/script.js
└── templates/
├── index.html
└── analytics.html



## ⚙️ Configuration

The application can be configured via `application.yml`:

spring:
data:
mongodb:
uri: mongodb://localhost:27017/url_shortener

server:
port: 8080

springdoc:
swagger-ui:
path: /swagger-ui.html



## 📈 Database Schema

### URLs Collection
{
"_id": "ObjectId",
"shortId": "abc123",
"originalUrl": "https://example.com",
"title": "Example Website",
"createdAt": "2025-01-01T12:00:00",
"expiresAt": "2025-02-01T12:00:00",
"clickCount": 42,
"active": true
}



### Click Events Collection
{
"_id": "ObjectId",
"shortId": "abc123",
"ipAddress": "192.168.1.1",
"userAgent": "Mozilla/5.0...",
"referer": "https://google.com",
"clickedAt": "2025-01-01T12:30:00"
}



## 🎯 Key Features Details

### Custom Aliases
- 3-20 characters long
- Alphanumeric characters and hyphens only
- Uniqueness validation
- Optional feature for branded links

### Analytics Tracking
- **Click Count**: Total clicks per URL
- **Geographic Data**: Country-based statistics
- **Referrer Analysis**: Traffic source tracking
- **Temporal Patterns**: Clicks over time
- **Recent Activity**: Latest click events

### URL Expiration
- Set expiration in days (1-365)
- Automatic cleanup of expired URLs
- Graceful handling of expired link access

## 🚀 Production Deployment

### Docker Deployment
FROM openjdk:21-jdk-slim
COPY target/url-shortener-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]



### Environment Variables
SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/url_shortener
SERVER_PORT=8080



## 🧪 Testing

Run tests with:
mvn test



## 📝 API Documentation

Interactive API documentation is available at `/swagger-ui.html` when the application is running. The documentation includes:

- **Request/Response Examples**
- **Schema Definitions**
- **Try It Out** functionality
- **Error Response Codes**
- **Authentication Requirements**

## 🔒 Security Considerations

- Input validation for all endpoints
- URL format validation
- Custom alias sanitization
- Rate limiting recommended for production
- HTTPS recommended for production

## 📊 Performance Features

- **MongoDB Indexing**: Optimized queries for short IDs
- **Efficient Schema**: Minimized document size
- **Connection Pooling**: Spring Data MongoDB optimization
- **Caching**: Consider Redis for high-traffic scenarios

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Support

For questions or issues:
- Create an issue in the repository
- Check existing documentation
- Review API examples in Swagger UI

**Built with ❤️ using Java 21 and Spring Boot**