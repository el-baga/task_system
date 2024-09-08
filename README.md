Данная инструкция поможет вам настроить и запустить проект локально.

Предварительные условия.
Прежде чем начать, убедитесь, что у вас установлены следующие программы:

- Java Development Kit (JDK): Версия 17 или выше. https://www.oracle.com/java/technologies/javase-downloads.html

- Apache Maven: Для управления зависимостями и сборки проекта. https://maven.apache.org/download.cgi

- Git: Для клонирования репозитория. https://git-scm.com/downloads

Клонирование репозитория:
cd existing_repo
git remote add origin https://github.com/el-baga/task_system.git
git branch -M master  
git push -uf origin master

Сборка и запуск проекта
- Откройте терминал в корневой директории проекта.
- Выполните команду для сборки проекта: mvn clean install
- После успешной сборки выполните команду для запуска проекта: mvn spring-boot:run

Проект использует файл application.yml для управления настройками приложения. По умолчанию он находится в директории src/main/resources. Убедитесь, что этот файл содержит актуальные для вашего окружения настройки.