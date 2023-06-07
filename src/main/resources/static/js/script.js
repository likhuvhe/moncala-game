function createGame() {
            fetch('/game/create', { method: 'POST' })
                .then(response => response.json())
                .then(data => {
                    const gameId = data.id;
                    window.location.href = '/game/' + gameId;
                })
                .catch(error => console.error('Error:', error));
        }