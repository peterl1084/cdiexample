# Vaadin Java EE app example

This is a work in progress Vaadin CDI example project that could be a starting point for a larger Java EE app.

Stuff that this app is built on:

 * Java EE 7
 * Vaadin
 * JPA
 * Apache Shiro
 * Vaadin CDI helper
 * CDI events
 * MVP
 * The new Valo theme for Vaadin to make it look modern
 
TODO:

 * Shiro plugin for "customer login"
 * Limited UI for customers to edit their own details
 * Localization
 * Clean up, blog posts etc.

## To get started (plaining with this app in your dev environment):

Build should be IDE/platform indendent. So just

 * Checkout the the project with `git clone --recursive git@github.com:peterl1084/cdiexample.git`. `--recursive` needed for cloning `git submodule` for [the theme](https://github.com/mstahv/dawn).
 * Configure your development server (e.g. GlassFish or WildFly) to contain data source "jdbc/example-backend"
 * Build + Run/Debug

