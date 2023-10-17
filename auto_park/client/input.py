import requests

from requessts import checkAutoTable, checkPersonnelTable, checkRoutesTable, checkJournalTable, editAutosTable, \
    editPersonnelTable, editRoutesTable, editJournalTable


def getUserToken(username, password):
    endpoint = "http://localhost:8080/api/login"
    request_body = {
        "username": f"{username}",
        "password": f"{password}"
    }
    headers = {'Content-Type': 'application/x-www-form-urlencoded'}
    response = requests.post(endpoint, data=request_body, headers=headers)
    s = response.text
    return s[s.find(':\"') + 2:s.find('\",\"')]


def getTokenAndLogIn():
    print('<<< Напишите \'exit\' чтобы выйти >>>')
    while True:
        userName = input("Введите имя пользователя:\n")
        if userName == 'exit':
            return '1'
        password = input("Введите пароль:\n")
        userToken = getUserToken(userName, password)
        if not userToken == '':
            break
    return userToken


def doMainCycle():
    while True:
        try:
            print("1 - Войти в учетную запись")
            print("2 - Выйти из программы")
            x = input()
            if x == '1':
                userToken = getTokenAndLogIn()
                if not userToken == '1':
                    logInSystem(userToken)
            if x == '2':
                break
        except Exception as e:
            print(e)


def logInSystem(token):
    print("Вход выполнен успешно")
    while True:
        print("1 - Просмотреть таблицы")
        print("2 - Редактировать таблицы")
        print("8 - Выход в главное меню")
        print("9 - Выход из программы")
        x = input()
        if x == '1':
            checkTable(token)
        if x == '2':
            editTable(token)
        if x == '8':
            return
        if x == '9':
            exit(0)


def printInfo():
    print("Выберите табрицу:")
    print("1 - Таблица машин")
    print("2 - Таблица водителей")
    print("3 - Таблица маршрутов")
    print("4 - Журнал поездок")
    print("8 - Назад")
    print("9 - Выход из программы")


def checkTable(token):
    while True:
        printInfo()
        x = input()
        if x == '1':
            checkAutoTable(token)
        if x == '2':
            checkPersonnelTable(token)
        if x == '3':
            checkRoutesTable(token)
        if x == '4':
            checkJournalTable(token)
        if x == '8':
            break
        if x == '9':
            exit(0)


def editTable(token):
    endpoint = "http://localhost:8080/api/user/log"
    headers = {"Authorization": f"Bearer {token}"}
    response = requests.post(endpoint, headers=headers)
    if response.status_code == 403:
        print('У вас нет доступа для данной операции.')
        return
    while True:
        printInfo()
        x = input()
        if x == '1':
            editAutosTable(token)
        if x == '2':
            editPersonnelTable(token)
        if x == '3':
            editRoutesTable(token)
        if x == '4':
            editJournalTable(token)
        if x == '8':
            break
        if x == '9':
            exit(0)


def fillTables():
    # Route
    tk = getUserToken('admin', 'admin')
    headers = {"Authorization": f"Bearer {tk}"}
    endpoint = "http://localhost:8080/routes/addNew"
    requests.post(endpoint, json={"name": 'Москва'}, headers=headers)
    requests.post(endpoint, json={'name': 'Питер'}, headers=headers)
    requests.post(endpoint, json={'name': 'Казань'}, headers=headers)
    requests.post(endpoint, json={'name': 'Новгород'}, headers=headers)
    requests.post(endpoint, json={'name': 'Нижневартовск'}, headers=headers)
    requests.post(endpoint, json={'name': 'Мурманск'}, headers=headers)
    requests.post(endpoint, json={'name': 'Барнаул'}, headers=headers)
    requests.post(endpoint, json={'name': 'Новокузнецк'}, headers=headers)
    requests.post(endpoint, json={'name': 'Октябрьский'}, headers=headers)
    requests.post(endpoint, json={'name': 'Уфа'}, headers=headers)
    # Person
    endpoint = "http://localhost:8080/personnels/addNew"
    requests.post(endpoint, json={
        "firstName": "Александр",
        "lastName": "Плавских",
        "patherName": "Ильич"
    }, headers=headers)
    requests.post(endpoint, json={
        "firstName": "Илья",
        "lastName": "Свайкин",
        "patherName": "Сергеевич"
    }, headers=headers)
    requests.post(endpoint, json={
        "firstName": "Артём",
        "lastName": "Пузиков",
        "patherName": "Николаевич"
    }, headers=headers)
    requests.post(endpoint, json={
        "firstName": "Данил",
        "lastName": "Артамонов",
        "patherName": "Сергеевич"
    }, headers=headers)
    requests.post(endpoint, json={
        "firstName": "Даниил",
        "lastName": "Валеев",
        "patherName": "Ильшатович"
    }, headers=headers)
    # Auto
    endpoint = "http://localhost:8080/autos/addNewByParams"
    requests.post(endpoint + '?num=r221u2&color=grey&mark=kia&personnel_id=15', headers=headers)
    requests.post(endpoint + '?num=a892qx&color=black&mark=honda&personnel_id=16', headers=headers)
    requests.post(endpoint + '?num=t493sx&color=pink&mark=audi&personnel_id=17', headers=headers)
    requests.post(endpoint + '?num=a555aa&color=yellow&mark=tesla&personnel_id=18', headers=headers)
    requests.post(endpoint + '?num=t999et&color=red&mark=reno&personnel_id=19', headers=headers)
    # Journal
    endpoint = "http://localhost:8080/journals/addNewByParams"
    requests.post(endpoint + '?time_in=2020-05-13&time_out=2020-06-18&route_id=5&auto_id=20', headers=headers)
    requests.post(endpoint + '?time_in=2020-05-14&time_out=2020-06-19&route_id=6&auto_id=21', headers=headers)
    requests.post(endpoint + '?time_in=2020-05-15&time_out=2020-06-20&route_id=7&auto_id=22', headers=headers)
    requests.post(endpoint + '?time_in=2020-05-16&time_out=2020-06-21&route_id=8&auto_id=23', headers=headers)
