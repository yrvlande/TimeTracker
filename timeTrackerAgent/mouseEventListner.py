from pynput.mouse import Listener
import logging

logging.basicConfig(filename="mouse_log.txt", level=logging.DEBUG, format='%(asctime)s&%(message)s', datefmt='%Y-%m-%d %H:%M:%S')


def on_move(x, y):
    logging.info('{0}:{1}'.format(x, y))


def on_click(x, y, button, pressed):
    if pressed:
        logging.info('{0}:{1}'.format(x, y))


def on_scroll(x, y):
    logging.info('{0}:{1}'.format(x, y))


with Listener(on_click=on_click) as listener:
    listener.join()
