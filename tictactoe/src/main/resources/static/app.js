var app = angular.module('ticTacToeApp', []);

app.controller('MainCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.selectedMode = 'PvB';
    $scope.board = [
        [0, 0, 0],
        [0, 0, 0],
        [0, 0, 0]
    ];
    $scope.currentPlayer = 'PLAYER1';
    $scope.gameId = null;
    $scope.winner = null;
    $scope.winLineClass = null;

    $scope.startGame = function () {
        $scope.winner = null; // Сбрасываем победителя
        $scope.winLineClass = null; // Сбрасываем линию
        $http.post('/tic-tac-toe/start?gameMode=' + $scope.selectedMode)
            .then(function (response) {
                $scope.board = response.data.board;
                $scope.currentPlayer = response.data.playerType;
                $scope.gameId = response.data.gameId;
                $scope.joinUrl = window.location.origin + '/tic-tac-toe/game/?game=' + response.data.gameId;
            })
            .catch(function (error) {
                console.error('Ошибка при старте игры:', error);
            });
    };

    $scope.makeMove = function (row, col) {
        if ($scope.board[row][col] !== 0 || !$scope.gameId || $scope.winner) return;

        const data = {
            gameId: $scope.gameId,
            board: $scope.board,
            gameMode: $scope.selectedMode,
            playerType: $scope.currentPlayer
        };

        $http.post('/tic-tac-toe/move?row=' + row + '&column=' + col, data)
            .then(function (response) {
                $scope.board = response.data.board;
                $scope.currentPlayer = response.data.playerType;
                $scope.winner = response.data.winner;

                // Определяем линию победителя
                if ($scope.winner) {
                    $scope.winLineClass = determineWinLine($scope.board);
                }
            })
            .catch(function (error) {
                console.error('Ошибка хода:', error);
            });
    };

    $scope.getSymbol = function (cellValue) {
        switch (cellValue) {
            case 1: return 'X';
            case 2: return 'O';
            case -1: return 'O';
            default: return '';
        }
    };

    $scope.getWinnerSymbol = function (winner) {
        if (winner === 'PLAYER1') return 'X';
        if (winner === 'PLAYER2') return 'O';
        if (winner === 'BOT') return 'O'
        if (winner === 'EMPTY') return 'Ничья'
        return winner;
    };

    function determineWinLine(board) {
        for (let i = 0; i < 3; i++) {
            if (board[i][0] !== 0 && board[i][0] === board[i][1] && board[i][1] === board[i][2]) {
                return 'win-line-vertical-' + i;
            }
        }
        for (let i = 0; i < 3; i++) {
            if (board[0][i] !== 0 && board[0][i] === board[1][i] && board[1][i] === board[2][i]) {
                return 'win-line-horizontal-' + i;
            }
        }
        if (board[0][0] !== 0 && board[0][0] === board[1][1] && board[1][1] === board[2][2]) {
            return 'win-line-diagonal-1';
        }
        if (board[0][2] !== 0 && board[0][2] === board[1][1] && board[1][1] === board[2][0]) {
            return 'win-line-diagonal-2';
        }
        return null;
    }
}]);