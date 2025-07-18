# Movie & Series Tracker

Welcome to the 2-week OG-CS Summer Internship!

In the following text you will read project requirements, coding and collaboration guidelines.

:warning: Read your task and guidelines carefully!

## Diagram

![Component Diagram](/docs/component-diagram.drawio.png)

## Requirements

### Technical

- You are tasked with designing (database) and creating a backend service in **Spring Boot**
- Make sure your code adheres to the best programming practices
- You will use **Postgres Database** and run it inside Docker using **Docker Compose** file
- To configure the application, it is recommended to use the **'application.yml'** file instead of 'application.properties'. This YAML-based configuration file allows for a more readable and organized approach to managing application properties.
- As a build tool, use **Gradle** instead of Maven.
- For confidential information inside application.yml (database credentials, ...) use **environment variables**

### Organizational

- Divide the project into smaller tasks so each team member can focus on specific modules while contributing equally
- Work together on key tasks like database design and project setup
- Keep in touch with your teammates to share progress and ideas (teams, discord, ...)

## User stories

### User Data Management

As a user, I want to provide basic information (name, email) so that the app can personalize my experience and associate records to me without requiring an account.

### Movie Management

As a user, I want to create a new movie record with title, genre, director, release year, description and duration so that movies can exist in the system.

As a user, I want to update movie details (title, genres, director, release year, duration, description) so that movie information remains accurate.

As a user, I want to delete a movie record so that incorrect or duplicate movies can be removed from the system.

### Series Management

As a user, I want to create a new TV series record with title, genres, creator, description and start year so that series can exist in the system.

As a user, I want to update series information (title, genres, creator, start year, description) so that series information remains accurate.

As a user, I want to delete a series record so that incorrect or duplicate series can be removed from the system.

### Watch Status Management

As a user, I want to mark movies and series with different statuses (watched, watching, plan to watch) so that I can organize my content.

As a user, I want to view content filtered by status so that I can easily find what I'm currently watching or planning to watch.

### Movie and Series Library Usage

As a user, I want to choose a movie from an existing library so that I do not need to create a new movie record if it already exists.

As a user, I want to choose a TV series from an existing library so that I do not need to create a new series record if it already exists.
