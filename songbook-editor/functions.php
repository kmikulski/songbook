<?php

function initConn() {
    $db = new PDO('sqlite:songbook.db');
    $db->exec('create table if not exists songs (title text, content text, chords text)');
    return $db;
}

function getExistingSongs($db) {
    $result = $db->query("select title, content, chords, rowid from songs order by title");
    return $result;
}

function getSong($db, $id) {
    $songs = $db->query("select title, content, chords, rowid from songs where rowid = $id");
    return $songs->fetch();
}

function saveNewSong($db, $title, $content, $chords) {
    if(empty($title) || empty($content)) {
        return;
    }
    $st = $db->prepare("insert into songs(title, content, chords) values(?, ?, ?)");
    return $st->execute(array($title, $content, $chords));
}

function updateFromPost($db) {
    $id = $_POST["id"];
    $title = $_POST["title"];
    $content = $_POST["content"];
    $chords = $_POST["chords"];
    if(empty($id) || empty($title) || empty($content) || empty($chords)) {
        return false;
    }
    $st = $db->prepare("update songs set title = ?, content = ?, chords = ? where rowid = ?");
    return $st->execute(array($title, $content, $chords, $id));
}

?>
