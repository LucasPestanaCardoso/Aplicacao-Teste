<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html ng-app="cadastroPessoa">
<head>
	<meta charset="UTF-8">
	<title>Lista Telefonica</title>
 	<link rel="stylesheet" type="text/css" href="bootstrap-3.3.7-dist/css/bootstrap.css">
	
	<style>
		.jumbotron {
			width: 600px;
			text-align: center;
			margin-top: 20px;
			margin-left: auto;
			margin-right: auto;
		}
		h3 {
			margin-bottom: 30px;
		}
		.table {
			margin-top: 20px;
		}
		.form-control {
			margin-bottom: 5px;
		}
		.selecionado {
			background-color: yellow;
		}
		.negrito {
			font-weight: bold;
		}
		hr {
			border-color: #999;
		}
	</style>
		
		 <script type="text/javascript" src="<spring:url value="angular/angular.js"/>"></script>
	     <script type="text/javascript" src="<spring:url value="angular/angular.min.js"/>"></script>
		 <script type="text/javascript" src="<spring:url value="angular/cadastropessoaCtrl.js"/>"></script>
	
</head>
<body ng-controller="cadastropessoaCtrl">
	<div class="jumbotron">
	    <h2>{{message}}</h2>
		<h3>{{app}}</h3>
	

   <input ng-show="contatos.length > 0" class="form-control" type="text" ng-model="criterioDeBusca" placeholder="O que você está buscando?"/>
   <table ng-show="contatos.length > 0" class="table table-striped">
   	 
   	 <tr>
   	 	<th>Nome</th>
   	 	<th>CPF</th>
   	 	<th>Data</th>
   	 	<th>Telefone</th>
   	 	<th colspan="2">Ações</th>
   	 </tr>
   	 <tr ng-repeat="contato in contatos | filter:criterioDeBusca ">
   	     <td> {{contato.nome}}</td>
   	     <td> {{contato.cpf}}</td>
		 <td>{{contato.data | date:'dd/MM/yyyy HH:mm'}}</td>
   	     <td> {{contato.telefone}} </td> 
   	     <td> <input type="button" name="Apagar" value="Apagar" class="btn btn-danger btn-block" ng-click="apagarContato(contato)"> </td>
   	     <td> <input type="button" name="Alterar" value="Alterar" class="btn btn-success btn-block" ng-click="alterarContato(contato)"> </td>
   	 </tr>
   </table>
   
   	<form name="contatoForm">
	   <input class="form-control" type="text" ng-model="contato.nome" name="nome" placeholder="Nome"  ng-required="true" />
	   <input class="form-control" type="text" ng-model="contato.cpf" name="cpf" placeholder="CPF" />
	   <input class="form-control" type="text" ng-model="contato.data" name="data" placeholder="Data" />
	   <input class="form-control" type="text" ng-model="contato.telefone" name="telefone" placeholder="Telefone"  />
	</form>
		
 
	 <div ng-show="contatoForm.nome.$dirty && contatoForm.nome.$error.required">
			<div ng-message="required" class="alert alert-danger">
				Por favor, preencha o campo nome!
			</div>
	</div>
 
			
	<button class="btn btn-primary btn-block" ng-click="adicionarContato(contato)"  ng-disabled="contatoForm.nome.$error.required">Salvar</button>
	<button class="btn btn-warning btn-block" ng-disabled="!contatos.length > 0"   ng-click="gerarExcel(contatos)">Gerar Excel</button>	
				
		
	</div>
</body>
</html>
