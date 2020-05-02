import { Router } from '@angular/router';
import { SessionService } from './session.service';
import { Component, OnInit } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent implements OnInit {

  public selectedIndex = 0;

  public appPages;

  public appPagesStudent = [

    {
      title: 'My Profile',
      url: '/profile',
      icon: 'person'
    },
    {
      title: 'My Applications',
      url: '/applications',
      icon: 'code-slash'
    },
    {
      title: 'Apply for Projects',
      url: '/projects',
      icon: 'code-working'
    },
    {
      title: 'Form A Team',
      url: '/teams',
      icon: 'people'
    },
    {
      title: 'Find A Company',
      url: '/viewAllCompanies',
      icon: 'business'
    },
    {
      title: 'Leave A Review',
      url: '/reviews',
      icon: 'ribbon-outline'
    },
  ];

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    public sessionService: SessionService,
  ) {
    this.initializeApp();
    this.updateMainMenu();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  ngOnInit() {

    console.log('********** AppComponent.ngOnInit()');

    const path = window.location.pathname;

    if (path !== undefined) {
      this.selectedIndex = this.appPages.findIndex(page => page.title.toLowerCase() === path.toLowerCase());
    }

    this.updateMainMenu();
  }

	ionViewWillEnter()
	{
		console.log('********** AppComponent.ionViewWillEnter()');		
	}
		
	onActivate(componentReference)
	{
		console.log('********** AppComponent.onActivate: ' + componentReference.componentType);
    this.updateMainMenu();
	}
	
	updateMainMenu()
	{
		if(this.sessionService.getIsLogin())
		{
			this.appPages  = [
				{
					title: 'Home',
					url: '/home',
					icon: 'home'
				},
				{
					title: 'Logout',
					url: '/login',
					icon: 'exit'
				}
			];
		}
		else
		{
			this.appPages  = [
				{
					title: 'Login',
					url: '/login',
					icon: 'lock-open'
        },
        {
					title: 'Register',
					url: '/createNewStudent',
					icon: 'person-add'
				}
			];
		}
  }
  
}
