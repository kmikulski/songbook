<html>
<head>
    <title>Pieśniczek WWW</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>

<?php
require_once("functions.php");
$db = initConn();

if(empty($_GET["id"])) {
    $songs = getExistingSongs($db);
?>

<h2>Spis treści</h2>
<ol>
<?php foreach($songs as $song) {
$id = $song["rowid"];
echo "<li><a href=editor.php?id=$id><img src=edit.png style='padding: 5px 5px 0 0;'></a>".
     "<a href=index.php?id=$id>".$song["title"]."</a></li>\n";
}?>
</ol>

<?php
} else {
    $song = getSong($db, $_GET["id"]);
    $title = $song["title"];
    $content = nl2br($song["content"]);
    $content = str_replace("    ", "&nbsp;&nbsp;&nbsp;&nbsp;", $content);
    $chords = nl2br($song["chords"]);
?>
<h2><?php echo $title; ?></h2>
<div>
    <div style="width: 450px; display: inline-block; vertical-align: top;"><?php echo $content; ?></div>
    <div style="width: 300px; display: inline-block; vertical-align: top;"><?php echo $chords; ?></div>
</div>
<h3><a href=index.php>Powrót</a></h3>
<?php
}
?>

</body>
</html>
