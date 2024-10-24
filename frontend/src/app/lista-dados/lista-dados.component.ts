import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../interfaces/Usuarios';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { PaginatorModule } from 'primeng/paginator';
import { TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lista-dados',
  standalone: true,
  imports: [TableModule, ButtonModule, DialogModule, InputTextModule, PaginatorModule, CommonModule],
  templateUrl: './lista-dados.component.html',
  styleUrl: './lista-dados.component.css'
})
export class ListaDadosComponent {
  usuarios: Usuario[] = [];
  tabelaMensagens: boolean = false;

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    this.buscarDados()
  }

  buscarDados() {
    this.httpClient.get<Usuario[]>(`http://localhost:8081/dados/`).subscribe(
      res => {
        this.usuarios = res; // Armazena a resposta na propriedade usuarios
        console.log(this.usuarios);
      },
      error => {
        console.error('Erro ao buscar dados:', error);
      }
    );
  }

  expandedUsuarioId: number | null = null; // Controla qual subtabela está expandida

  toggleMensagens(usuarioId: number) {
    this.expandedUsuarioId = this.expandedUsuarioId === usuarioId ? null : usuarioId; // Alterna a visibilidade
  }
}
