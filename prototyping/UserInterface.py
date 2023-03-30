import sys
from PyQt5.QtWidgets import (
    QApplication,
    QWidget,
    QPushButton,
    QMenu,
    QMainWindow,
    QAction
)

from constants import *


class Window(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        # 主视图，待办事项
        task_button = QPushButton("Tasks", self)
        task_button.resize(BUTTON_WIDTH, BUTTON_HEIGHT)
        task_button.move(0, SCREEN_HEIGHT - BUTTON_HEIGHT)

        # 月历视图，日程
        view_button = QPushButton("View", self)
        view_button.resize(BUTTON_WIDTH, BUTTON_HEIGHT)
        view_button.move(BUTTON_WIDTH, SCREEN_HEIGHT - BUTTON_HEIGHT)

        # 切换升序、降序
        switch_button = QPushButton("switch", self)
        switch_button.resize(BUTTON_WIDTH // 2, BUTTON_HEIGHT)
        switch_button.move(SCREEN_WIDTH - BUTTON_WIDTH // 2, 0)

        # sort mode
        sort_button = QPushButton("SORT_MODE", self)
        sort_button.resize(BUTTON_WIDTH // 2, BUTTON_HEIGHT)
        sort_button.move(SCREEN_WIDTH // 3, 0)

        # logo
        logo_button = QPushButton("LOGO", self)
        logo_button.resize(BUTTON_WIDTH // 2, BUTTON_HEIGHT)

        # add
        add_button = QPushButton("ADD", self)
        add_button.resize(BUTTON_WIDTH // 2, BUTTON_HEIGHT)
        add_button.move(SCREEN_WIDTH - BUTTON_WIDTH // 2, SCREEN_HEIGHT - BUTTON_HEIGHT * 3)

        content = "Left is the item's Title. \t\t\t Right is the countdown."

        content_button = QPushButton("Content", self)
        content_button.resize(2 * BUTTON_WIDTH - 100, 4 * BUTTON_HEIGHT - 40)
        content_button.move(50, 4 * BUTTON_HEIGHT)
        item1_button = QPushButton(content, self)
        # item2_button = QPushButton(content, self)
        # item3_button = QPushButton(content, self)
        item4_button = QPushButton(content, self)
        item5_button = QPushButton(content, self)
        item1_button.resize(BUTTON_WIDTH * 2, BUTTON_HEIGHT * 2)
        # item2_button.resize(BUTTON_WIDTH * 2, BUTTON_HEIGHT * 2)
        # item3_button.resize(BUTTON_WIDTH * 2, BUTTON_HEIGHT * 2)
        item4_button.resize(BUTTON_WIDTH * 2, BUTTON_HEIGHT * 2)
        item5_button.resize(BUTTON_WIDTH * 2, BUTTON_HEIGHT * 2)
        item1_button.move(0, 2 * BUTTON_HEIGHT)
        # item2_button.move(0, 4 * BUTTON_HEIGHT)
        # item3_button.move(0, 6 * BUTTON_HEIGHT)
        item4_button.move(0, 8 * BUTTON_HEIGHT)
        item5_button.move(0, 10 * BUTTON_HEIGHT)

        # 设置窗口长宽和位置
        self.setGeometry(INIT_X, INIT_Y, SCREEN_WIDTH, SCREEN_HEIGHT)
        self.setWindowTitle(TITLE)
        self.show()


if __name__ == '__main__':
    app = QApplication(sys.argv)

    window = Window()

    sys.exit(app.exec_())
