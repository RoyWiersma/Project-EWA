# Front-end site
This repo contains all the code for the front-end site of our project. In this readme I will explain how to install and
run the server.

## Hardware Requirements
* Minimum of 4 GB RAM
* Minimum of 5 GB of diskspace

## Software Requirements
* Linux or Windows OS
* NginX or Apache webserver

## How to build and run the backend server
1. First you need to install NPM or Yarn to install all the dependencies and make the build. To install them, follow 
there official documentation [NPM installation](https://www.npmjs.com/get-npm),
[Yarn installation](https://yarnpkg.com/getting-started)
2. After you've installed one package manager, you can use the command `npm install` or `yarn install` to download all 
the packages required for this project.
3. If you've finished downloading the dependencies, you need to install the Angular CLI. You can install it by following
the official documentation [Angular CLI installation](https://angular.io/guide/setup-local#install-the-angular-cli).
4. To make sure the site works, you can run the unit test by typing the following command in the terminal `ng test`
5. If all the test succeeded, you can make a build for either the production or development. Run `ng build` to build the
project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.
6. When the build is finished, you can upload the files that are generated in the `dist/` folder to the hosting.
