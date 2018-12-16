(ns christmas.day13-test
 (:require [christmas.day13 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))
(comment
(defn get-file [file-name]
	(->> (slurp file-name)
		 str/split-lines))

(def file (get-file "resources/day11-15/Input13"))
(def realFile (get-file "resources/day11-15/Input13real"))

(def carts-init {[9 3] {:spd [0 1] :pos [9 3] :dir :right}
				 [2 0] {:spd [1 0] :pos [2 0] :dir :right}})

(def rules {[7 1] "/", [2 2] "/", [0 0] "/", [2 5] "`", [7 2] "+", [7 4] "`", [4 2] "+", [12 1] "`", [12 4] "/", [2 4] "+", [9 2] "`", [0 4] "`", [9 5] "/", [9 4] "+", [4 4] "/", [4 0] "`"})

(expect [[[2 0](carts-init [2 0])]] (chr/cart-row 0 "/ > \\        "))
(expect [] (chr/cart-row 0 "       /    \\"))
;(expect "" file)


(expect carts-init (chr/get-carts file))

(expect rules (chr/get-rules file))
(expect 16 (count (chr/get-rules file)))

(expect {:spd [1 0] :pos [6 3] :dir :right} (chr/go-on {:spd [1 0] :pos [5 3] :dir :right}))
(expect {:spd [0 -1] :pos [0 7] :dir :right} (chr/go-on {:spd [0 -1] :pos [0 8] :dir :right}))

(expect {:spd [1 0] :pos [1 8] :dir :right} 
	(chr/do-slash {:spd [0 -1] :pos [0 8] :dir :right}))

(expect {:spd [0 -1] :pos [2 1] :dir :right} 
	(chr/do-backslash {:spd [-1 0] :pos [2 2] :dir :right}))

(expect :left (chr/next-dir :right))

(expect {:spd [-1 0] :pos [1 2] :dir :straight} 
	(chr/do-crossing {:spd [-1 0] :pos [2 2] :dir :left}))

(expect {:spd [0 1] :pos [2 3] :dir :left} 
	(chr/do-crossing {:spd [-1 0] :pos [2 2] :dir :right}))

(expect {:spd [0 -1] :pos [2 1] :dir :right} 
	(chr/do-crossing {:spd [-1 0] :pos [2 2] :dir :straight}))

(expect 
	{[2 -1] {:spd [0 -1] :pos [2 -1] :dir :left} 
	 [9 3] {:spd [0 1] :pos [9 3] :dir :right}}
	(chr/tick [2 0] carts-init {[2 0] "+"}))

(expect {[2 0] {:spd [1 0] :pos [2 0] :dir :right}}
	(chr/check-crash {:spd [0 1] :pos [9 3] :dir :right} carts-init))

#_(expect carts-init 
	(chr/check-crash {:spd [0 1] :pos [3 3] :dir :right} carts-init))

;(expect [7 3] (chr/something file))

(expect [[2 1][1 2][4 4][9 4][4 6][9 6]] 
	(chr/order-positions [[9 6][9 4][4 6][4 4][2 1][1 2]]))

(expect [[3 0][9 4]] (keys (chr/one-tick [[2 0][9 3]] carts-init rules)))

;(expect [69 67] (chr/something realFile))





)
















;)