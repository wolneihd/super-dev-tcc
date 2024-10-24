import requests
import json

def save_text_message_DB(mensagem):
    url = "http://localhost:8081/dados/nova-mensagem"
    data = [
        {
            "userId": mensagem.from_user.id,
            "firstName": mensagem.from_user.first_name,
            "lastName": mensagem.from_user.last_name,
            "mensagens": [
                {
                    "id": 1,
                    "tiposMensagem": {
                        "id": 1,
                        "tipo": "texto"
                    },
                    "timestampCod": mensagem.date,
                    "textMsg": mensagem.json['text']
                }
            ]
        }
    ]

    headers = {
        "Content-Type": "application/json"  # Especifica que o corpo da requisição é JSON
    }

    # Fazendo a requisição POST
    response = requests.post(url, headers=headers, data=json.dumps(data))

    # Verificando o status da resposta
    if response.status_code == 200:
        print("Dados enviados com sucesso:", response.json())
    else:
        print("Erro ao enviar dados:", response.status_code, response.text)


def save_audio_message_DB(mensagem, audio_transcrito:str):
    url = "http://localhost:8081/dados/nova-mensagem"
    data = [
        {
            "userId": mensagem.from_user.id,
            "firstName": mensagem.from_user.first_name,
            "lastName": mensagem.from_user.last_name,
            "mensagens": [
                {
                    "id": 1,
                    "tiposMensagem": {
                        "id": 3,
                        "tipo": "audio"
                    },
                    "timestampCod": mensagem.date,
                    "textMsg": audio_transcrito
                }
            ]
        }
    ]

    headers = {
        "Content-Type": "application/json"  # Especifica que o corpo da requisição é JSON
    }

    # Fazendo a requisição POST
    response = requests.post(url, headers=headers, data=json.dumps(data))

    # Verificando o status da resposta
    if response.status_code == 200:
        print("Dados enviados com sucesso:", response.json())
    else:
        print("Erro ao enviar dados:", response.status_code, response.text)