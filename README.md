# Simple Cache App

This is a simple cache app that is run from the command line. It allows you to store, retrieve, and delete key-value pairs in a cache. The cache is stored in a WeakHaspMap, which means that the keys are weakly referenced and can be garbage collected if there are no strong references to them. This is useful for caching data that is expensive to compute or retrieve, as it allows the cache to automatically free up memory when it is no longer needed.

## Features

- Store key-value pairs in a cache
- Retrieve values from the cache
- Delete values from the cache
- Print cache contents
- Print the size of the cache
- Automatic garbage collection of unused keys
- Command line interface for easy use

## How to Contribute

For the contributor covenant to this project, please check the Code of Conduct file.

[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)