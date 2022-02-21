from pynput.mouse import Listener as MouseListener
from pynput.keyboard import Listener as KeyboardListener
import logging


logging.basicConfig(filename="mouse_log.txt", level=logging.DEBUG, format='%(asctime)s&%(message)s', datefmt='%Y-%m-%dT%H:%M:%S')


def on_move(x, y):
    logging.info('{0}:{1}'.format(x, y))


def on_click(x, y, button, pressed):
    if pressed:
        logging.info('{0}:{1}'.format(x, y))


def on_scroll(x, y):
    logging.info('{0}:{1}'.format(x, y))


def on_press(key):
    logging.info('0:0')


keyboard_listener = KeyboardListener(on_press=on_press)
mouse_listener = MouseListener(on_click=on_click)
keyboard_listener.start()
mouse_listener.start()
keyboard_listener.join()
mouse_listener.join()
