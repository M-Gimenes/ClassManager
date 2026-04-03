# ClassManager

A desktop application for managing a private tutoring business, built with **Java 21** and **JavaFX**. The project covers the full operational cycle — from student registration to revenue analytics — and explores how to integrate multiple technologies (local persistence, PDF reporting, and remote gRPC communication) within a single, cohesive desktop application.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 (LTS) |
| GUI | JavaFX 21 + FXML |
| Build | Maven |
| Database | SQLite via JDBC |
| Reports | JasperReports |
| Remote Communication | gRPC + Protocol Buffers |

## Architecture

The project is structured around a strict **MVC** separation, with a dedicated **DAO layer** isolating all database logic from the rest of the application. Each entity has its own DAO class responsible exclusively for its SQLite queries, keeping controllers free of persistence concerns and making the data layer independently testable.

```
src/classmanager/
├── controller/     # JavaFX FXML controllers (one per screen)
├── model/
│   ├── dao/        # Data Access Objects — one per entity, all SQLite logic lives here
│   └── domain/     # Plain domain entities (Student, Lesson, ClassGroup, Skill…)
├── grpc/           # gRPC client, async service wrapper, Protobuf-generated stubs
├── reports/        # Compiled JasperReports templates (.jasper)
└── Main.java       # Application entry point and scene manager
```

## Features

- **Students** — CRUD with birth date, contact info, school, and class assignment
- **Class Groups** — group creation, member management, and per-group reporting
- **Lessons** — scheduling and tracking of lessons per group, with associated skills
- **Skills** — tag each lesson with the topics/skills covered
- **Payments** — record and monitor payment status per student
- **Attendance Report** — generates a structured PDF from a JasperReports template, populated with live SQLite data
- **Revenue Chart** — visual breakdown of revenue per class group over time
- **Lesson Chart** — bar chart showing lesson frequency, useful for workload analysis

## gRPC Integration

One of the main technical explorations in this project is the integration of a **gRPC client** within a JavaFX desktop app — two paradigms that require deliberate bridging.

The client communicates asynchronously with a remote gRPC server (defined via `.proto` schemas and compiled with the Protobuf Maven plugin). Since gRPC calls run on background threads, results are dispatched back to the JavaFX Application Thread using `Platform.runLater()`, ensuring UI updates remain thread-safe. The application starts and operates normally when the server is unavailable, with graceful error handling surfaced directly in the UI.

## Running

**Prerequisites:** Java 21+, Maven 3.6+

```bash
mvn javafx:run
```

> The gRPC features require a compatible server running on `localhost`. All other functionality works fully offline.
