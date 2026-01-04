# 📝 Todo App Backend API

---

Bu proje, kullanıcıların günlük görevlerini yönetebildiği, güvenli ve rol tabanlı erişim kontrolüne sahip bir Spring Boot uygulamasıdır.

### 🚀 Kullanılan Teknolojiler

* **Java 21**
* **Spring Boot 4.0.1**
* **Spring Web:** RESTful API endpoint'leri için.
* **Spring Data JPA (Hibernate):** Veritabanı işlemleri ve ORM için.
* **Spring Security:** Endpoint güvenliği ve JWT (JSON Web Token) tabanlı kimlik doğrulama için.
* **MySQL:** Veritabanı.
* **OpenAPI / Swagger:** API dokümantasyonu için.

### ✨ Temel Özellikler

* **Rol Bazlı Erişim:** **Admin** ve **Employee** (Standart Kullanıcı) olmak üzere iki farklı rol.
* **Akıllı Admin Atama:** Sisteme kayıt olan **ilk kullanıcı** otomatik olarak `ADMIN` yetkisi alır. Sonraki kullanıcılar `EMPLOYEE` olarak başlar.
* **Veri İzolasyonu:** Kullanıcılar sadece **kendi** oluşturdukları görevleri görebilir, düzenleyebilir ve silebilir.
* **Görev Yönetimi:** Görev ekleme, listeleme, silme ve "Tamamlandı/Devam Ediyor" durumunu değiştirme (Toggle).
* **Admin Yetkileri:** Adminler tüm kullanıcıları görebilir, kullanıcı silebilir veya mevcut bir kullanıcıyı Admin yapabilir.
* **Güvenli Profil:** Şifre değiştirme ve hesap silme işlemleri (Son Admin kendini silemez kuralı ile korunur).

### 🔐 API Güvenlik Mimarisi: JWT (Stateless)

Bu API, modern ve ölçeklenebilir **JWT (JSON Web Token)** tabanlı Stateless (Durumsuz) kimlik doğrulama yapısı kullanır.

API'yi kullanmak için aşağıdaki akış takip edilmelidir:
1.  `POST /api/auth/login` endpoint'ine JSON formatında `email` ve `password` gönderin.
2.  Sunucu, yanıt olarak başarılı girişte `200 OK` ve bir **Access Token (JWT)** döndürür.
3.  Korumalı endpoint'lere (örn: `/api/todos`) istek atarken, bu token'ı isteğin **Header** kısmına eklemeniz gerekir:
    `Authorization: Bearer <TOKEN>`
4.  Sunucu tarafında oturum tutulmadığı için çıkış işlemi istemci tarafında Token'ın silinmesi ile yapılır.

### 🗺️ API Endpoint Rehberi

#### 🏠 Genel
| Metot | URL | Açıklama |
| :--- | :--- | :--- |
| `GET` | `/swagger-ui/index.html` | Swagger arayüzüne erişim (Tarayıcıdan). |

#### 🔑 Kimlik Doğrulama (Auth) - (Herkese Açık)
| Metot | URL | Body (Request) | Açıklama |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | JSON (Ad, Soyad, Email, Şifre) | Yeni kullanıcı kaydı oluşturur. (İlk kayıt Admin olur). |
| `POST` | `/api/auth/login` | JSON (Email, Şifre) | Giriş yapar ve **JWT Token** döndürür. |

#### 📝 İş Takibi (Todos) - (Korumalı)
*(Header'da Token gereklidir)*

| Metot | URL | Body (Request) | Açıklama |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/todos` | (Boş) | Giriş yapmış kullanıcının **tüm** görevlerini listeler. |
| `POST` | `/api/todos` | JSON (Title, Description, Priority) | Yeni bir görev oluşturur. |
| `PUT` | `/api/todos/{id}` | (Boş) | ID'si verilen görevin durumunu tersine çevirir (Tamamlandı <-> Tamamlanmadı). |
| `DELETE` | `/api/todos/{id}` | (Boş) | ID'si verilen görevi siler (Sadece sahibi silebilir). |

#### 👤 Profil Yönetimi (Users) - (Korumalı)
| Metot | URL | Body (Request) | Açıklama |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/users/info` | (Boş) | Giriş yapmış kullanıcının profil bilgilerini getirir. |
| `PUT` | `/api/users/password` | JSON (Eski Şifre, Yeni Şifre, Yeni Şifre Tekrar) | Şifre değiştirme işlemi yapar. |
| `DELETE` | `/api/users` | (Boş) | Kullanıcının kendi hesabını siler. (Son Admin kendini silemez). |

#### 🛡️ Yönetici Paneli (Admin) - (Sadece Admin Erişebilir)
| Metot | URL | Body / Param | Açıklama |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/admin` | (Boş) | Sistemdeki **tüm** kullanıcıları listeler. |
| `PUT` | `/api/admin/{userId}/role` | Path Variable (userId) | Belirtilen kullanıcıyı **Admin** yetkisine yükseltir. |
| `DELETE` | `/api/admin/{userId}` | Path Variable (userId) | Belirtilen kullanıcıyı (Admin değilse) sistemden siler. |

---

This project is a Spring Boot application that allows users to manage their daily tasks, featuring secure and role-based access control.

### 🚀 Technologies Used

* **Java 21**
* **Spring Boot 4.0.1**
* **Spring Web:** For RESTful API endpoints.
* **Spring Data JPA (Hibernate):** For database operations and ORM.
* **Spring Security:** For endpoint security and JWT (JSON Web Token) based authentication.
* **MySQL:** Database.
* **OpenAPI / Swagger:** For API documentation.

### ✨ Key Features

* **Role-Based Access:** Two different roles: **Admin** and **Employee** (Standard User).
* **Smart Admin Assignment:** The **first user** to register in the system is automatically assigned the `ADMIN` role. Subsequent users start as `EMPLOYEE`.
* **Data Isolation:** Users can only view, edit, and delete tasks created by **themselves**.
* **Task Management:** Add, list, delete, and toggle task status (Completed/In Progress).
* **Admin Privileges:** Admins can view all users, delete users, or promote an existing user to Admin.
* **Secure Profile:** Password change and account deletion operations (Protected by the "Last Admin cannot delete themselves" rule).

### 🔐 API Security Architecture: JWT (Stateless)

This API uses a modern and scalable **JWT (JSON Web Token)** based Stateless authentication structure.

To use the API, follow this flow:
1.  Send `email` and `password` in JSON format to the `POST /api/auth/login` endpoint.
2.  Upon successful login, the server returns `200 OK` and an **Access Token (JWT)**.
3.  When making requests to protected endpoints (e.g., `/api/todos`), you must add this token to the **Header** of the request:
    `Authorization: Bearer <TOKEN>`
4.  Since no session is held on the server side, the logout process consists of deleting the Token on the client side.

### 🗺️ API Endpoint Guide

#### 🏠 General
| Method | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/swagger-ui/index.html` | Access to Swagger UI (via Browser). |

#### 🔑 Authentication (Auth) - (Public)
| Method | URL | Body (Request) | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | JSON (Name, Surname, Email, Password) | Creates a new user record. (First registration becomes Admin). |
| `POST` | `/api/auth/login` | JSON (Email, Password) | Logs in and returns a **JWT Token**. |

#### 📝 Task Tracking (Todos) - (Protected)
*(Token required in Header)*

| Method | URL | Body (Request) | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/todos` | (Empty) | Lists **all** tasks of the logged-in user. |
| `POST` | `/api/todos` | JSON (Title, Description, Priority) | Creates a new task. |
| `PUT` | `/api/todos/{id}` | (Empty) | Toggles the status of the task with the given ID (Completed <-> Not Completed). |
| `DELETE` | `/api/todos/{id}` | (Empty) | Deletes the task with the given ID (Only the owner can delete). |

#### 👤 Profile Management (Users) - (Protected)
| Method | URL | Body (Request) | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/users/info` | (Empty) | Retrieves profile information of the logged-in user. |
| `PUT` | `/api/users/password` | JSON (Old Password, New Password, Confirm New Password) | Performs password change operation. |
| `DELETE` | `/api/users` | (Empty) | Deletes the user's own account. (The last Admin cannot delete themselves). |

#### 🛡️ Admin Panel (Admin) - (Admin Only)
| Method | URL | Body / Param | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/admin` | (Empty) | Lists **all** users in the system. |
| `PUT` | `/api/admin/{userId}/role` | Path Variable (userId) | Promotes the specified user to **Admin** role. |
| `DELETE` | `/api/admin/{userId}` | Path Variable (userId) | Deletes the specified user (if not Admin) from the system. |
