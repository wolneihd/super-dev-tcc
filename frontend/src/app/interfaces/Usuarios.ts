// tipos-mensagem
export interface TiposMensagem {
  id: number;
  tipo: string;
}

// mensagem
export interface Mensagem {
  id: number;
  tiposMensagem: TiposMensagem;
  timestampCod: number;
  textMsg: string;
  feedback: string;
  categoria: string;
  analise_ia: string;
}

// usuario
export interface Usuario {
  id: number;
  userId: number;
  firstName: string;
  lastName: string;
  mensagens: Mensagem[];
}
