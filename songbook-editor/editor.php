<?php
require_once("functions.php");
$conn = initConn();
if($_POST["update"]) {
    $status = updateFromPost($conn);
    header('Location: index.php');
} else if(empty($_GET["id"])) {
    header('Location: index.php');
} else {
    $song = getSong($conn, $_GET["id"]);
    $title = $song["title"];
    if(strtoupper($title) === $title) {
        $title = ucfirst(strtolower($title));
    }
?>
<html>
<head>
    <title>Edytor śpiewnika</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>

<form action="editor.php" method="post">

    <div>
        <label>Tytuł:</label><input size=50 type=text name="title" value="<?php echo $title;?>">
    </div>
    
<textarea cols=60 rows=60 name="content" style="resize: none;" placeholder="Tekst piosenki">
<?php echo $song["content"];?>
</textarea>
<textarea cols=30 rows=60 name="chords" style="resize: none;" placeholder="Akordy">
<?php echo $song["chords"];?>
</textarea>
    
    <input type="hidden" name="update" value="true">
    <input type="hidden" name="id" value="<?php echo $song["rowid"];?>">
    
    <div><input type="submit"></div>

</form>


</body>
</html>
<?php
}
?>