import { Component } from '@angular/core';

@Component({
	selector: 'app-server',
	templateUrl: './server.component.html',


	/*We can write the html directly in .ts
	as shown below*/


	// template:'<h1> Buddodu </h1><app-servers></app-servers>',
})
export class ServerComponent{
	serverId: number = 11;
	serverStatus :string ='offline';

	getServerStatus(){
	return this.serverStatus;
	}

	getServerLead(){
	return "ROBBY";
	}
}


