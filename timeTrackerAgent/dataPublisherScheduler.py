# Schedule Library imported
import schedule
import time
import requests


def publishData():
    requestJson = { "userId": "e1078065", "mouseClickTime": "2022-02-17T13:10:17", "xCoordinate": 10,
                   "yCoordinate": 11}
    try:
        response = requests.post("http://localhost:8087/event/data", json=requestJson)
        print(response.status_code)
        print(response.json())
    except:
        print("Connection Refused")


schedule.every(10).seconds.do(publishData)

while True:
    schedule.run_pending()
    time.sleep(1)
