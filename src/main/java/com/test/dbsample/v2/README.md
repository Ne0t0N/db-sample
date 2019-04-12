## Part 1 - Simple file storage with read/write/update operations by key ##

# How to store data in DB
 - by generated key (DB generates a key and returns it when an entry is written)
 - by provided key (user provides a key and then DB should somehow normalize and store it)
    - !!! store 'num:' before key to identify key length and where value starts (for example, 12:key:value), requires finite key length
    - typed key (for example, integer or UUID)
    - safe symbol key (string/varchar type, exception if key contains incorrect symbols), delimiter is the main problem
    - hash of the key (DB normalizes provided key, collision may take effect)
    