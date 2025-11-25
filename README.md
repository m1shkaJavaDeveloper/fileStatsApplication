# FileStats

Консольная утилита для подсчёта статистики файлов в каталоге.

Собирает статистику по каждому расширению:

- Количество файлов
- Размер в байтах
- Количество строк
- Количество непустых строк
- Количество строк с комментариями (для Java и Bash)

Поддерживает фильтрацию по расширениям, многопоточность, игнорирование файлов из `.gitignore` и вывод в разных форматах.

---

## Сборка

Собрать JAR с зависимостями:

```bash
cd C:\Projects\FILESTAT\sources
mvn clean package

```


Аргументы

1. <path> - путь до каталога по которому надо выполнить сбор статистики
2. --recursive - выполнять обход дерева рекурсивно
3. --max-depth=<number> - глубина рекурсивного обхода
4. --thread=<number> - количество потоков используемого для обхода
5. --include-ext=<ext1,ext2,ext3,..> - обрабатывать файлы только с указанными расширениями
6. --exclude-ext=<ext1,ext2,ext3,..> - не обрабатывать файлы с указанными расширениями
7. --git-ignore - не обрабатывать файлы указанные в файле .gitignore
8. --output=<plain,xml,json> - формат вывода статистики

Примеры запуска

```bash
# 1. Простейший анализ каталога src
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src

# 2. Рекурсивно и только java/md файлы
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src --recursive --include-ext=java,md

# 3. С игнорированием файлов из .gitignore
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src --recursive --git-ignore

# 4. С ограничением глубины обхода
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src --recursive --max-depth=2

# 5. Использовать 10 потоков
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src --recursive --thread=10

# 6. Исключить определённые расширения
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src --recursive --exclude-ext=log,tmp

# 7. Вывод в JSON
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src --recursive --output=json

# 8. Вывод в XML
java -jar target/filestats-1.0-SNAPSHOT-shaded.jar src --recursive --output=xml

