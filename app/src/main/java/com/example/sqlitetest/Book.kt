package com.example.sqlitetest

class Book {
    var id: Int = 0
    var bookName: String = ""
    var authorName: String = ""

    constructor(id: Int, bookName: String, author: String) {
        this.id = id
        this.bookName = bookName
        this.authorName = author
    }

    constructor(bookName: String, authorName: String) {
        this.bookName = bookName
        this.authorName = authorName
    }

    constructor()
}