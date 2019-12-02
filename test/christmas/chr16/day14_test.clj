(ns christmas.chr16.day14-test
  (:require [christmas.chr16.day14 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def input "jlmsuwbz")
(def t-input "abc")

;(expect "" (chr/get-64-keys input))

(expect \o (chr/contains-triple? "efajkshdfoooajh"))
(expect nil (chr/contains-triple? "efajkshdfooajh"))
(expect \j (chr/contains-triple? "efajjjjkshdfoooajh"))

(expect [\o] (chr/contains-5tuple? "efajjjkshdfoooooajh"))
(expect [\j \o] (chr/contains-5tuple? "efajjjjjkshdfoooooajh"))

(expect {:pad-keys [] :candidates {18 \8}, :hash "abc" :digestions 1} 
        (chr/process-next {:pad-keys [] :candidates {} :hash t-input :digestions 1} 18))
(expect {:pad-keys [39], :candidates {816 \e}, :hash "abc" :digestions 1} 
        (chr/process-next {:pad-keys [] :candidates {39 \e} :hash t-input :digestions 1} 816))

;(expect 35186 (chr/process input 1))  ; 22728 too low ; 35408 too high
;(expect 22429 (chr/process input 2017))  
)