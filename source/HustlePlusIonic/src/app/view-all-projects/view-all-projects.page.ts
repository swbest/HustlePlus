import { Component, OnInit } from '@angular/core';

import { ProjectService } from '../project.service';
import { Project } from '../project';

@Component({
  selector: 'app-view-all-projects',
  templateUrl: './view-all-projects.page.html',
  styleUrls: ['./view-all-projects.page.scss'],
})

export class ViewAllProjectsPage implements OnInit {

	projects: Project[];
	errorMessage: string;

	constructor(private projectService: ProjectService) {		}

	ngOnInit()
	{
		this.projectService.getProjects().subscribe(
			response => {
				this.projects = response.projects
			},
			error => {
				this.errorMessage = error
			}
		);
	}
}
