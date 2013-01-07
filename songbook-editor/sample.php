<html>
<head>
    <title>Java Sample</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>

<?php
require_once("functions.php");
$db = initConn();
$songs = getExistingSongs($db);

echo "<code>String[] TEST_ARRAY = new String[]{<br>\n";

foreach($songs as $song) {
    $title = $song["title"];
    echo "\"$title\",<br>";
}

echo "};"
?>

</body>
</html>
