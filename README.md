unicorn-play
============

A collaborative listening web application. Allow everyone connected to your
Local Area Network to add music to the playlist using a web browser.

## Dependencies

`unicorn-play` requires the following external dependencies:

1. A working installation of the [Music Player Daemon](http://mpd.wikia.com/wiki/Install) (`mpd`).
2. Maven (`mvn`), a java build tool.

Bare-bones configuration files for MPD are included in this repository. It is
recommended that you install a simple MPD client (such as `mpc`) to test
whether MPD’s audio output is working before installing the system.

## Installation

Using `git` from the command line:

```sh
git clone https://github.com/matthugs/unicorn-play.git
cd unicorn-play
mpd lin-mpd.conf # change according to your platform: win-mpd.conf or osx-mpd.conf
cd collab-webapp
mvn jetty:run-war
```

This last command starts the server running on port 8080. If you’d like to use
a different port, you can use a command-line flag:

```sh
mvn -Djetty.port=9007 jetty:run-war
```

## Using

Once both `mpd` and the server are running on your machine, you’ll be able to
visit the application by visiting http://localhost:8080/collab-webapp in a web
browser (such as Google Chrome). You will also be able to access the interface
from any other machine on your Local Area Network, using your IP address in
place of localhost.

### Library Setup

The default location where `mpd` looks for music files is `~/Music`. This can
be changed by the following line in your platform’s `mpd.conf` file:
```conf
music_directory		"~/Music"
```

Change the path in parentheses to the root directory of wherever you store
music files.
