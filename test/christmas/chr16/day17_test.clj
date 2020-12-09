(ns christmas.chr16.day17-test
  (:require [christmas.chr16.day17 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def code (slurp "resources/chr16/day17real"))

(expect ["D"] (chr/tick "hijkl" [""]))

(expect nil (chr/program "hijkl"))

(expect "DDRRRD" (chr/program "ihgpwlah"))
(expect "DDUDRLRRUDRD" (chr/program "kglvqrro"))
(expect "DRURDRUDDLLDLUURRDULRLDUUDDDRR" (chr/program "ulqzkmiv"))

(expect "DDRLRRUDDR" (chr/program code))
(expect 556 (chr/program2 code))

)

