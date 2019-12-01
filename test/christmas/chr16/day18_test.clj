(ns christmas.chr16.day18-test
  (:require [christmas.chr16.day18 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/chr16/day18real"))

(comment
(expect "^" (chr/trap? ["^" "^" "."]))
(expect "^" (chr/trap? ["." "^" "^"]))
(expect "^" (chr/trap? ["^" "." "."]))
(expect "^" (chr/trap? ["." "." "^"]))
(expect "." (chr/trap? ["^" "." "^"]))
(expect "." (chr/trap? ["." "^" "."]))
(expect "." (chr/trap? ["^" "^" "^"]))
(expect "." (chr/trap? ["." "." "."]))

(expect ["." "^" "^" "^" "^"] (chr/next-row ["." "." "^" "^" "."]))

(expect 38 (chr/tile-room ".^^.^.^^^^" 10)) 
(expect 1926 (chr/tile-room file 40)) 
(expect 1996597 (chr/tile-room file 400000)) 
)