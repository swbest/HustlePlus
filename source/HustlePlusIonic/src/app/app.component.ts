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
  public appPages = [
    {
      title: 'Home',
      url: '/home',
      icon: 'home'
    },
    {
      title: 'View All Projects',
      url: '/viewAllProjects',
      icon: 'arrow-forward'
    },
    {
      title: 'Create New Student',
      url: '/createNewStudent',
      icon: 'arrow-forward'
    },
    {
      title: 'View All Student',
      url: '/viewAllStudents',
      icon: 'arrow-forward'
    },
    {
      title: 'View Student Details',
      url: '/viewStudentDetails',
      icon: 'arrow-forward'
    },
    {
      title: 'View All Companies',
      url: '/viewAllCompanies',
      icon: 'arrow-forward'
    },
    {
      title: 'View Company Details',
      url: '/viewCompanyDetails',
      icon: 'arrow-forward'
    },
    {
      title: 'Create New Review',
      url: '/createNewReview',
      icon: 'arrow-forward'
    },
    {
      title: 'Create New Student Review',
      url: '/createNewStudentReview',
      icon: 'arrow-forward'
    },
    {
      title: 'Create New Company Review',
      url: '/createNewCompanyReview',
      icon: 'arrow-forward'
    },
    {
      title: 'Login',
      url: '/login',
      icon: 'arrow-forward'
    }
  ];

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  ngOnInit() {
    const path = window.location.pathname.split('folder/')[1];
    if (path !== undefined) {
      this.selectedIndex = this.appPages.findIndex(page => page.title.toLowerCase() === path.toLowerCase());
    }
  }
}
