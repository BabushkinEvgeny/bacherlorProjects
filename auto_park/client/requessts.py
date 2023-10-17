from prettytable import PrettyTable
import requests

def checkToken(request):
    if "Token has expired" in request:
        raise Exception("The Token has expired")


def showAutoTable(request):
    table = PrettyTable()
    table.field_names = ["№", "Номер", "Цвет", "Марка", "Имя водителя"]
    for i in range(len(request)):
        table.add_row(
            [i + 1, request[i]["num"], request[i]["color"], request[i]["mark"], request[i]["personnelId"]["firstName"]])
    print(table)


def showPersonTable(request):
    table = PrettyTable()
    table.field_names = ["№", "Id", "Имя", "Фамилия", "Отчество"]
    for i in range(len(request)):
        table.add_row(
            [i + 1, request[i]["id"], request[i]["firstName"], request[i]["lastName"], request[i]["patherName"]])
    print(table)


def showRouteTable(request):
    table = PrettyTable()
    table.field_names = ["№", "Название маршрута"]
    for i in range(len(request)):
        table.add_row(
            [i + 1, request[i]["name"]])
    print(table)


def showJournalTable(request):
    table = PrettyTable()
    table.field_names = ["№", "Время выезда", "Время приезда","Id машины", "Имя водителя", "Номер машины", "Id маршрута",
                         "Название маршрута"]
    for i in range(len(request)):
        table.add_row(
            [i + 1, request[i]["timeIn"][0:10], request[i]["timeOut"][0:10], request[i]["autoId"]["id"],
             request[i]["autoId"]["personnelId"]["firstName"],
             request[i]["autoId"]["num"], request[i]["routeId"]["id"], request[i]["routeId"]["name"]])
    print(table)


def checkAutoTable(token):
    endpoint = "http://localhost:8080/autos/getAll"
    headers = {"Authorization": f"Bearer {token}"}
    request = requests.get(endpoint, headers=headers)
    checkToken(request.text)
    request = request.json()
    showAutoTable(request)


def checkPersonnelTable(token):
    endpoint = "http://localhost:8080/personnels/getAll"
    headers = {"Authorization": f"Bearer {token}"}
    request = requests.get(endpoint, headers=headers)
    checkToken(request.text)
    request = request.json()
    showPersonTable(request)


def checkJournalTable(token):
    while True:
        print('1 - Показать все записи')
        print('2 - Найти запись по id маршрута')
        print('8 - Назад')
        print('9 - Выход из программы')
        endpoint = "http://localhost:8080/journals/"
        headers = {"Authorization": f"Bearer {token}"}
        x = input()
        if x == '1':
            endpoint += 'getAll'
            request = requests.get(endpoint, headers=headers)
            checkToken(request.text)
            request = request.json()
            showJournalTable(request)
        if x == '2':
            id = input('Введите id маршрута\n')
            try:
                request = requests.get(endpoint + f'getById?routeId={id}', headers=headers)
                checkToken(request.text)
                request = request.json()
                print(request)
            except:
                print('Записи с таким маршрутом нет в базе.')
        if x == '8':
            return
        if x == '9':
            exit(0)


def checkRoutesTable(token):
    while True:
        headers = {"Authorization": f"Bearer {token}"}
        endpoint = "http://localhost:8080/routes/"
        print('1 - Показать все маршруты')
        print('2 - Найти маршрут по id')
        print('8 - Назад')
        print('9 - Выход из программы')
        x = input()
        if x == '1':
            endpoint += 'getAll'
            request = requests.get(endpoint, headers=headers)
            checkToken(request.text)
            request = request.json()
            showRouteTable(request)
        if x == '2':
            id = input('Введите id маршрута\n')
            try:
                request = requests.get(endpoint + f'getRouteById?id={id}', headers=headers)
                checkToken(request.text)
                request = request.json()
                print(request)
            except:
                print("Такого маршрута нет в базе")
        if x == '8':
            return
        if x == '9':
            exit(0)


def editRoutesTable(token):
    while True:
        ep = "http://localhost:8080/routes/getAll"
        headers = {"Authorization": f"Bearer {token}"}
        request = requests.get(ep, headers=headers)
        checkToken(request.text)
        request = request.json()
        showRouteTable(request)
        endpoint = "http://localhost:8080/routes/"
        print('1 - Добавить маршрут')
        print('2 - Удалить маршрут')
        print('8 - Назад')
        print('9 - Выход из программы')
        x = input()
        if x == '1':
            endpoint += 'addNew'
            headers = {"Authorization": f"Bearer {token}"}
            name = input('Введите имя маршрута:\n')
            response = requests.post(endpoint, json={"name": name}, headers=headers)
            if response.status_code == 200:
                print('Маршрут успешно добавлен')
            else:
                print('Произошла ошибка во время добавления')
        if x == '2':
            endpoint += 'deleteByName'
            headers = {"Authorization": f"Bearer {token}"}
            name = input('Введите имя маршрута\n')
            response = requests.delete(endpoint + f'?name={name}', headers=headers)
            if response.status_code == 200:
                print('Маршрут успешно удалён')
            else:
                print('Произошла ошибка во время удаления')
        if x == '8':
            return
        if x == '9':
            exit(0)


def editAutosTable(token):
    while True:
        ep = "http://localhost:8080/autos/getAll"
        headers = {"Authorization": f"Bearer {token}"}
        request = requests.get(ep, headers=headers)
        checkToken(request.text)
        request = request.json()
        showAutoTable(request)
        endpoint = "http://localhost:8080/autos/"
        print('1 - Добавить машину')
        print('2 - Изменить цвет машины')
        print('3 - Изменить номер машины')
        print('4 - Удалить машину')
        print('8 - Назад')
        print('9 - Выход из программы')
        x = input()
        if x == '1':
            endpoint += 'addNewByParams'
            headers = {"Authorization": f"Bearer {token}"}
            num = input('Введите номер автомобиля:\n')
            color = input('Введите цвет автомобиля:\n')
            mark = input('Введите марку автомобиля:\n')
            vId = input('Введите id водителя:\n')
            response = requests.post(endpoint + f'?num={num}&color={color}&mark={mark}&personnel_id={vId}',
                                     headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Автомобиль успешно добавлен')
            else:
                print('Произошла ошибка во время добавления')

        if x == '2':
            endpoint += 'setNewColor'
            headers = {"Authorization": f"Bearer {token}"}
            color = input('Введите новый цвет автомобиля\n')
            id = input('Введите id автомобиля\n')
            response = requests.put(endpoint + f'?id={id}&newColor={color}', headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Цвет успешно изменён')
            else:
                print('Произошла ошибка во время изменения')

        if x == '3':
            endpoint += 'setNewNum'
            headers = {"Authorization": f"Bearer {token}"}
            num = input('Введите новый номер автомобиля\n')
            id = input('Введите id автомобиля\n')
            response = requests.put(endpoint + f'?id={id}&newNum={num}', headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Цвет успешно изменён')
            else:
                print('Произошла ошибка во время изменения')

        if x == '4':
            endpoint += 'deleteById'
            headers = {"Authorization": f"Bearer {token}"}
            id = input('Введите id водителя\n')
            response = requests.delete(endpoint + f'?id={id}', headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Автомобиль успешно удалён')
            else:
                print('Произошла ошибка во время удаления')

        if x == '8':
            return
        if x == '9':
            exit(0)


def editPersonnelTable(token):
    while True:
        ep = "http://localhost:8080/personnels/getAll"
        headers = {"Authorization": f"Bearer {token}"}
        request = requests.get(ep, headers=headers)
        checkToken(request.text)
        request = request.json()
        showPersonTable(request)
        endpoint = "http://localhost:8080/personnels/"
        print('1 - Добавить водителя')
        print('2 - Переименовать водителя')
        print('3 - Удалить водителя')
        print('8 - Назад')
        print('9 - Выход из программы')
        x = input()
        if x == '1':
            endpoint += 'addNew'
            headers = {"Authorization": f"Bearer {token}"}
            first = input('Введите имя водителя:\n')
            lastn = input('Введите фамилию водителя:\n')
            pathn = input('Введите отчество водителя:\n')
            data = {
                "firstName": first,
                "lastName": lastn,
                "patherName": pathn
            }
            response = requests.post(endpoint, json=data, headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Водитель успешно добавлен')
            else:
                print('Произошла ошибка во время добавления')

        if x == '2':
            endpoint += 'setNewName'
            headers = {"Authorization": f"Bearer {token}"}
            first = input('Введите новое имя водителя:\n')
            id = input('Введите id водителя\n')
            response = requests.put(endpoint + f'?newName={first}&id={id}', headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Имя успешно изменено')
            else:
                print('Произошла ошибка во время изменения')

        if x == '3':
            endpoint += 'deleteById'
            headers = {"Authorization": f"Bearer {token}"}
            id = input('Введите id водителя:\n')
            response = requests.delete(endpoint + f'?id={id}', headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Водитель успешно удалён')
            else:
                print('Произошла ошибка во время удаления')

        if x == '8':
            return
        if x == '9':
            exit(0)


def editJournalTable(token):
    while True:
        ep = "http://localhost:8080/journals/getAll"
        headers = {"Authorization": f"Bearer {token}"}
        request = requests.get(ep, headers=headers)
        checkToken(request.text)
        request = request.json()
        showJournalTable(request)
        endpoint = "http://localhost:8080/journals/"
        print('1 - Добавить запись')
        print('2 - Удалить запись')
        print('8 - Назад')
        print('9 - Выход из программы')
        x = input()
        if x == '1':
            endpoint += 'addNewByParams'
            headers = {"Authorization": f"Bearer {token}"}
            timeIn = input('Введите время выезда:\n')
            timeOut = input('Введите время приезда:\n')
            autoId = input('Введите id автомобиля:\n')
            routeId = input('Введите id маршрута:\n')
            response = requests.post(
                endpoint + f'?time_in={timeIn}&time_out={timeOut}&route_id={routeId}&auto_id={autoId}',
                headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Запись успешна добавлен')
            else:
                print('Произошла ошибка во время добавления')

        if x == '2':
            endpoint += 'deleteById'
            headers = {"Authorization": f"Bearer {token}"}
            id = input('Введите id записи\n')
            response = requests.delete(endpoint + f'?id={id}', headers=headers)
            checkToken(response.text)
            if response.status_code == 200:
                print('Запись успешно удалёна')
            else:
                print('Произошла ошибка во время удаления')

        if x == '8':
            return
        if x == '9':
            exit(0)
