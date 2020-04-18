import { Component, OnInit } from '@angular/core';

import { SessionService } from '../session.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})

export class HomePage implements OnInit
{

  
	constructor(public sessionService: SessionService)
	{		
	}



	ngOnInit()
	{
		console.log('********** HomePage.ngOnInit()');			
		
	}
	
	
	
	ionViewWillEnter()
	{
		console.log('********** HomePage.ionViewWillEnter()');		
	}
	
	
	parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}
}
