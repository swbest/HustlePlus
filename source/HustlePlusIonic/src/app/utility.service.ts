import { Injectable } from '@angular/core';
import { Platform } from '@ionic/angular';

@Injectable({
	providedIn: 'root'
})
export class UtilityService {

	constructor(private platform: Platform) { }

	getRootPath(): string {
		console.log('this.platform.is("hybrid"): ' + this.platform.is('hybrid'));
		if (this.platform.is('hybrid')) {
			// might need to change the ip address in the future
			return "http://192.168.137.1:8080/HustlePlusRws/Resources/";
		}
		else {
			return "/api/";
		}
	}
}
